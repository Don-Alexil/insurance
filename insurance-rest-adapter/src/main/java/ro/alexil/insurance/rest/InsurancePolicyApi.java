package ro.alexil.insurance.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface InsurancePolicyApi {

    default InsurancePolicyDelegate getDelegate() {
        return new InsurancePolicyDelegate() {};
    }

    @Operation(summary = "Get an  insurance policy by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the insurance policy",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsurancePolicyResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Insurance policy not found",
                    content = @Content) })
    @GetMapping("/insurancepolicies/{id}")
    default ResponseEntity<InsurancePolicyResponse> getInsurancePolicy(
            @Parameter(description = "id of the insurance policy to be searched")
            @PathVariable Long id) {
        return getDelegate().getInsurancePolicy(id);
    }

    @Operation(summary = "Get the list of insurance policies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list of the insurance policies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsurancePolicyResponse.class)) }) })
    @GetMapping("/insurancepolicies")
    default ResponseEntity<List<InsurancePolicyResponse>> getAllInsurancePolicies() {
        return getDelegate().getAllInsurancePolicies();
    }

    @Operation(summary = "Create an insurance policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Insurance policy created created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsurancePolicyResponse.class)) })})
    @PostMapping("/insurancepolicies")
    default ResponseEntity<InsurancePolicyResponse> createInsurancePolicy(
            @Validated @RequestBody InsurancePolicyRequest insurancePolicyRequest){
        return getDelegate().createInsurancePolicy(insurancePolicyRequest);
    }

    @Operation(summary = "Update an insurance policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Insurance policy created created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsurancePolicyResponse.class)) })})
    @PatchMapping("/insurancepolicies/{id}")
    default ResponseEntity<InsurancePolicyResponse> updateInsurancePolicy(
            @Parameter(description = "id of the insurance policy to be searched")
            @PathVariable Long id,
            @Validated @RequestBody InsurancePolicyRequest insurancePolicyRequest){
        return getDelegate().updateInsurancePolicy(id, insurancePolicyRequest);
    }
}
