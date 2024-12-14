"use strict";

/* JS Script for adding a job to job page */
function mainJob()
{
    // Initialize Phone Mask for Phone Number Input
    initializePhoneMask();


    // Validate Job Data
    validateAddJobData();

    // Send AJAX Request To Backend
    sendAddNewJobAjaxRequestToBackend();
}

function validateAddJobData()
{
    // Validate Company Section
    $("#company-name").on({
        input: () => requiredFieldValidate("company-name"),
        focus: () => formControlFocusValidate("company-name"),
        blur: () => formControlBlurValidate("company-name")
    });

    // Validate Job Section
    $("#job-name").on({
        input: () => requiredFieldValidate("job-name"),
        focus: () => formControlFocusValidate("job-name"),
        blur: () => formControlBlurValidate("job-name")
    });

    // Validate Job Description
    $("#job-description").on({
        input: () => requiredFieldValidate("job-description"),
        focus: () => formControlFocusValidate("job-description"),
        blur: () => formControlBlurValidate("job-description")
    });

    // Validate Pay Rate Type
    $("#rate-type").on({
        input: () => requiredFieldValidate("rate-type"),
        focus: () => formControlFocusValidate("rate-type"),
        blur: () => formControlBlurValidate("rate-type")
    });

    // Validate Rate Amount
    $("#rate-amount").on({
        input: () => requiredFieldValidate("rate-amount"),
        focus: () => formControlFocusValidate("rate-amount"),
        blur: () => formControlBlurValidate("rate-amount")
    });

    // Validate Payment Day
    $("#payment-day").on({
        input: () => requiredFieldValidate("payment-day"),
        focus: () => formControlFocusValidate("payment-day"),
        blur: () => formControlBlurValidate("payment-day")
    });

    $("#total-pay").on({
        input: () => requiredFieldValidate("total-pay"),
        focus: () => formControlFocusValidate("total-pay"),
        blur: () => formControlBlurValidate("total-pay")
    });

    $("#tip-amount").on({
        input: () => requiredFieldValidate("tip-amount"),
        focus: () => formControlFocusValidate("tip-amount"),
        blur: () => formControlBlurValidate("tip-amount")
    });

}


function emptyAddJobFormFields()
{
    /** Empty form fields before unloading */

    // Empty all employer fields
    $("#company-name").val("");
    $("#company-email").val("");
    $("#company-phone-number").val("+1 (___) ___ - ____");

    // Empty all job fields
    $("#job-name").val("");
    $("#job-description").val("");

    // Empty all pay rate fields
    $("#rate-type").val("");
    $("#rate-amount").val("");

    // Empty all apy roll fields
    $("#payment-day").val("");
    $("#total-pay").val("");
    $("#tip-amount").val("");
}

function sendAddNewJobAjaxRequestToBackend()
{
    // Add an event listener for the add new job button
    $("#add-job-new-btn").on("click", function (e) {
        // Prevent Default Behaviour
        e.preventDefault();

        // Prepare data to be sent to the user
        // Grab User Id
        const user_id = $("#user_id").val();

        // Get company data in an Obj
        const companyData = {
            companyName: $("#company-name").val(),
            companyContactEmail: $("#company-email").val(),
            companyContactPhoneNumber: $("#company-phone-number").val()
        };

        // Get Job Data in Obj
        const jobData = {
            jobName: $("#job-name").val(),
            jobDescription: $("#job-description").val()
        };

        // Get Pay Rate Data in An Obj
        const payRateData = {
            payRateType: $("#rate-type").val(),
            payRateAmount: $("#rate-amount").val()
        };

        // Get Pay Check Data In An Obj
        const payCheckData = {
            payCheckPaymentDay: $("#payment-day").val(),
            payCheckTotalPayment: $("#total-pay").val(),
            payCheckTips: $("#tip-amount").val()
        };

        // Required Form Control Checks
        const requiredFormControls = [
            $("#company-name"),
            $("#job-name"),
            $("#job-description"),
            $("#rate-type"),
            $("#rate-amount"),
            $("#payment-day"),
            $("#total-pay"),
            $("#tip-amount")
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
            displayFormErrorAlert("job-page-content-wrapper", "Add All Required Job Data", false);
        }

        // Prepare JSON payload to send
        const data = JSON.stringify({
            "springUserId": user_id,
            "company": companyData,
            "job": jobData,
            "payRate": payRateData,
            "payCheck": payCheckData
        });

        if (doAjax)
        {
            // Run validation code once it is developed
            $.ajax({
                url: `${websiteURL}/dashboard/job/addNewJob`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function () {
                    smoothlyScrollToTop(".content-wrapper");
                },
                success: function (response) {
                    // Show Success Alert When New Job Could Be Added
                    const message = response["message"];

                    displayFormSuccessAlert("job-page-content-wrapper", message, false);
                    emptyAddJobFormFields();

                },
                error: function(xhr) {
                    // Show Error Alert When New Job Could Not Be Added

                    // Grab error message
                    let errorMsg = "New Job Could Not Be Added";

                    if (xhr.responseJSON["error"])
                    {
                        errorMsg = xhr.responseJSON["error"];
                    }

                    displayFormErrorAlert("job-page-content-wrapper", errorMsg, false);
                },
            });
        }

    });
}

$(document).ready(mainJob);


