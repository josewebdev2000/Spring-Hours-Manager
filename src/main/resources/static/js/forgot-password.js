"use strict";

function mainForgotPassword()
{
    // Reset forgot password form on reload
    resetForgotPasswordForm();

    // validate and submit forgot password form
    validateAndSubmitForgotPasswordForm();
}

function resetForgotPasswordForm()
{
    $(window).on("beforeunload", function() {
        $("#email").val("");
    });
}

/* Write Code for Front-End Forgot Password Form Validation and AJAX Sending */
function validateAndSubmitForgotPasswordForm()
{
    // Validate the email input
    $("#email").on({
        input: () => emailValidate("email"),
        focus: () => formControlFocusValidate("email"),
        blur: () => formControlBlurValidate("email")
    });

    // Add an event listener for the forgot-password button
    $("#forgot-password-form").on("submit", function (e) {
        e.preventDefault();

        // Control whether to do the AJAX request or not
        var doAjax = false;

        // Check the email input has the is-valid class
        if ($("#email").hasClass("is-valid"))
        {
            doAjax = true;
        }

        else
        {
            doAjax = false;

            if ($("#form-alerts-container").children().length < 1)
            {
                displayFormErrorAlert("form-alerts-container", "Enter a valid email address");
            }
        }

        // Perform AJAX request
        if (doAjax)
        {
            // Grab actual email value
            const email = $("#email").val();

            // Form JSON string to send to the back-end
            const data = JSON.stringify({
                email
            });

            // Grab original content from submit button
            const forgotPasswordBtnHTMLOri = $("#forgot-password").html();

            // Send AJAX Request
            $.ajax({
                url: `${websiteURL}/forgot-password`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    // Disable Button to avoid request before response
                    const forgotPasswordBtn = $("#forgot-password");
                    forgotPasswordBtn.html(loadingSpinner());
                    forgotPasswordBtn.prop("disabled", true);
                },
                success: function (response)
                {
                    displayFormSuccessAlert("form-alerts-container", response["success"]);
                },
                error: function (xhr)
                {
                    displayFormErrorAlert("form-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function() {
                    // Enable Forgot Password Button To Avoid Request Before Response
                    const forgotPasswordBtn = $("#forgot-password");
                    forgotPasswordBtn.html(forgotPasswordBtnHTMLOri);
                    forgotPasswordBtn.prop("disabled", false);
                }
            });
        }
    });
}

$(document).ready(mainForgotPassword);