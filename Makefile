.DEFAULT_TARGET: help
.PHONY: help build

# Documentation : https://www.gnu.org/software/make/manual/make.html

SHELL := /bin/bash
DEBUG ?= false
MAKEFLAGS := --no-print-directory

# VARS

# COLORS
.COLOR_NONE := \033[0m
.COLOR_RED := \033[31m
.COLOR_GREEN := \033[32m
.COLOR_ORANGE := \033[33m
.COLOR_BLUE := \033[36m
.PREFIX_ERROR := $(.COLOR_RED)==> [ERROR]$(.COLOR_NONE)
.PREFIX_WARN := $(.COLOR_ORANGE)==> [WARN]$(.COLOR_NONE)
.PREFIX_INFO := $(.COLOR_GREEN)==> [INFO]$(.COLOR_NONE)
.PREFIX_DEBUG := $(.COLOR_BLUE)==> [DEBUG]$(.COLOR_NONE)


help: ## Help me !
	@echo -e "$(.PREFIX_INFO) Help me !"
	@echo "Targets :"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "  - $(.COLOR_BLUE)%-30s$(.COLOR_NONE) %s\n", $$1, $$2}'
	@echo ""


init: ## Init resources
	@echo -e "$(.PREFIX_INFO) Create data folder ..."
	@mkdir -p ~/poc/data/mysql

run: ## Run Insurance stack locally !
	@echo -e "$(.PREFIX_INFO) Start insurance stack ..."
	@docker compose up -d
	@echo -e "$(.PREFIX_INFO) Insurance stack started ..."

stop: ## Stop stack - docker-compose down !!
	@echo -e "$(.PREFIX_INFO) Stop insurance ..."
	@docker compose down

cleanup: ## Cleanup the local insurance stack  - docker-compose down --volumes --remove-orphans !!
	@echo -e "$(.PREFIX_INFO) Cleanup insurance stack ..."
	@@docker compose down --volumes --remove-orphans

compile: ## Compile project !
	@echo -e "$(.PREFIX_INFO) Compile project ..."
	@@mvn clean install

build-rest: ## Build rest docker image !
	@echo -e "$(.PREFIX_INFO) Build rest docker image ..."
	@@docker build -t ro.alexil/insurance-rest-api -f insurance-app/Dockerfile .

build-vue: ## Build vue docker image !
	@echo -e "$(.PREFIX_INFO) Build vue docker image ..."
	@@cd ./vue-insurance-app && \
	docker build -t ro.alexil/insurance-vue-client .
	@cd ..

build: build-rest build-vue

tests: ## Test insurance!
	@echo -e "$(.PREFIX_INFO) Test insurance ..."
	@export INSURANCE=$(shell curl -s -d '@data/insurance_policy.json' -H 'Content-Type: application/json' -X POST http://localhost:8080/insurancepolicies | jq -r '.id') && \
	echo "Created insurance policy : $$INSURANCE"; \
	curl -s -H 'Content-Type: application/json' -X GET http://localhost:8080/insurancepolicies/$$INSURANCE | jq; \
	echo "Update insurance" && \
	curl -s -H 'Content-Type: application/json' -d '{"name": "uuidgen", "status": "ACTIVE", "startDate": "2024-10-20", "endDate": "2025-09-30"}' -X PATCH http://localhost:8080/insurancepolicies/$$INSURANCE | jq; \
    echo "List insurances" && \
    curl -s -H 'Content-Type: application/json' -X GET http://localhost:8080/insurancepolicies | jq


