"use strict";

/* JS Script for editing a job to a job page */
function mainEditJob()
{
    initializePhoneMask();

    // Validate Job Data
    validateEditJobData();

    // Send AJAX Request to Backend
    sendEditJobAjaxRequestToBackend();
}

function validateEditJobData()
{
    requiredFieldValidate("edit-company-name");
    requiredFieldValidate("edit-company-email");
    requiredFieldValidate("edit-company-phone-number");
    requiredFieldValidate("edit-job-name");
    requiredFieldValidate("edit-job-description");
    requiredFieldValidate("edit-rate-type");
    requiredFieldValidate("edit-rate-amount");
    requiredFieldValidate("edit-payment-day");
    requiredFieldValidate("edit-total-pay");
    requiredFieldValidate("edit-tip-amount");

    // Validate Company Section
    $("#edit-company-name").on({
        input: () => requiredFieldValidate("edit-company-name"),
        focus: () => formControlFocusValidate("edit-company-name"),
        blur: () => formControlBlurValidate("edit-company-name")
    });

    $("#edit-company-email").on({
        input: () => requiredFieldValidate("edit-company-email"),
        focus: () => formControlFocusValidate("edit-company-email"),
        blur: () => formControlBlurValidate("edit-company-email")
    });

    $("#edit-company-phone-number").on({
        input: () => requiredFieldValidate("edit-company-phone-number"),
        focus: () => formControlFocusValidate("edit-company-phone-number"),
        blur: () => formControlBlurValidate("edit-company-phone-number")
    });

    // Validate Job Section
    $("#edit-job-name").on({
        input: () => requiredFieldValidate("edit-job-name"),
        focus: () => formControlFocusValidate("edit-job-name"),
        blur: () => formControlBlurValidate("edit-job-name")
    });

    // Validate Job Description
    $("#edit-job-description").on({
        input: () => requiredFieldValidate("edit-job-description"),
        focus: () => formControlFocusValidate("edit-job-description"),
        blur: () => formControlBlurValidate("edit-job-description")
    });

    // Validate Pay Rate Type
    $("#edit-rate-type").on({
        input: () => requiredFieldValidate("edit-rate-type"),
        focus: () => formControlFocusValidate("edit-rate-type"),
        blur: () => formControlBlurValidate("edit-rate-type")
    });

    // Validate Rate Amount
    $("#edit-rate-amount").on({
        input: () => requiredFieldValidate("edit-rate-amount"),
        focus: () => formControlFocusValidate("edit-rate-amount"),
        blur: () => formControlBlurValidate("edit-rate-amount")
    });

    // Validate Payment Day
    $("#edit-payment-day").on({
        input: () => requiredFieldValidate("edit-payment-day"),
        focus: () => formControlFocusValidate("edit-payment-day"),
        blur: () => formControlBlurValidate("edit-payment-day")
    });

    $("#edit-total-pay").on({
        input: () => requiredFieldValidate("edit-total-pay"),
        focus: () => formControlFocusValidate("edit-total-pay"),
        blur: () => formControlBlurValidate("edit-total-pay")
    });

    $("#edit-tip-amount").on({
        input: () => requiredFieldValidate("edit-tip-amount"),
        focus: () => formControlFocusValidate("edit-tip-amount"),
        blur: () => formControlBlurValidate("edit-tip-amount")
    });
}

function sendEditJobAjaxRequestToBackend()
{
    // Add an event listener for the edit job button
    $("#edit-job-btn").on("click", function (e) {
        // Prevent Default Behaviour
        e.preventDefault();

        // Prepare data to be sent to the user
        // Grab User Id
        const editUserId = $("#edit_user_id").val();
        const editJobId = $("#edit_job_id").val();

        // Get company data in Obj
        const editCompanyData = {
            companyName: $("#edit-company-name").val(),
            companyContactEmail: $("#edit-company-email").val(),
            companyContactPhoneNumber: $("#edit-company-phone-number").val()
        };

        // Get Job Data in Obj
        const editJobData = {
            jobName: $("#edit-job-name").val(),
            jobDescription: $("#edit-job-description").val()
        };

        // Get Pay Rate Data in Obj
        const editPayRateData = {
            payRateType: $("#edit-rate-type").val(),
            payRateAmount: $("#edit-rate-amount").val()
        };

        // Get Pay Check Data In Obj
        const editPayCheckData = {
            payCheckPaymentDay: $("#edit-payment-day").val(),
            payCheckTotalPayment: $("#edit-total-pay").val(),
            payCheckTips: $("#edit-tip-amount").val()
        };

        // Required Form Control Checks
        const requiredFormControls = [
            $("#edit-company-name"),
            $("#edit-company-email"),
            $("#edit-company-phone-number"),
            $("#edit-job-name"),
            $("#edit-job-description"),
            $("#edit-rate-type"),
            $("#edit-rate-amount"),
            $("#edit-payment-day"),
            $("#edit-total-pay"),
            $("#edit-tip-amount")
        ];

        // Control whether AJAX request should be made
        var doAjax = false;

        for (let requiredFormControl of requiredFormControls)
        {
            if (requiredFormControl.hasClass("is-valid"))
            {
                doAjax = true;
            }

            else
            {
                doAjax = false;

                if (requiredFormControl.val().length == 0)
                {
                    requiredFormControl.removeClass("is-valid");
                    requiredFormControl.addClass("is-invalid");
                }

                else
                {
                    requiredFormControl.removeClass("is-invalid");
                    requiredFormControl.addClass("is-valid");
                }

            }
        }

        if (!doAjax)
        {
            smoothlyScrollToTop(".content-wrapper");
            displayFormErrorAlert("edit-job-page-content-wrapper", "Add All Required Job Data", false);
        }

        // Prepare JSON payload to send
        const data = JSON.stringify({
            "springUserId": editUserId,
            "company": editCompanyData,
            "job": editJobData,
            "payRate": editPayRateData,
            "payCheck": editPayCheckData
        });

        if (doAjax)
        {
            // Run validation code once it is developed
            $.ajax({
                url: `${websiteURL}/dashboard/job/editJob/${editJobId}`,
                method: "PUT",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    smoothlyScrollToTop(".content-wrapper")
                },
                success: function(response) {
                    // Dynamically Set New Values With Animation
                    displayFormSuccessAlert("job-page-content-wrapper", "This Job Was Modified", false);

                    const inputsNTextareas = $("input, textarea");

                    inputsNTextareas.fadeOut("slow");

                    // Change values here
                    $("#edit-job-name").val(response["jobName"]);
                    $("#edit-job-description").val(response["jobDescription"]);

                    $("#edit-company-name").val(response["companyDetailsView"]["companyName"]);
                    $("#edit-company-email").val(response["companyDetailsView"]["companyEmail"]);
                    $("#edit-company-phone-number").val(response["companyDetailsView"]["companyPhoneNumber"]);

                    $("#edit-rate-type").val(response["payRateDetailsView"]["payRateType"].toLowerCase());
                    $("#edit-rate-amount").val(response["payRateDetailsView"]["payRateAmount"]);

                    $("#edit-payment-day").val(response["payCheckDetailsView"]["payCheckPaymentDay"].toLowerCase());
                    $("#edit-total-pay").val(response["payCheckDetailsView"]["payCheckTotalPayment"]);
                    $("#edit-tip-amount").val(response["payCheckDetailsView"]["payCheckTips"]);

                    inputsNTextareas.fadeIn("slow");
                },
                error: function(xhr) {
                    let errorMsg = "Job Could not be Updated";

                    if (xhr.responseJSON["error"])
                    {
                        errorMsg = xhr.responseJSON["error"];
                    }

                    displayFormErrorAlert("edit-job-page-content-wrapper", errorMsg, false);
                }
            });
        }
    });
}

$(document).ready(mainEditJob);
