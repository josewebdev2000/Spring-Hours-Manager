"use strict";

function mainResetPassword()
{
    // Init Reset Password Showers
    initResetPasswordPasswordShowers();

    // Reset password form on reload
    resetResetPasswordForm();

    // validate and submit reset password form
    validateAndSubmitResetPasswordForm();
}

function resetResetPasswordForm()
{
    $(window).on("beforeunload", function() {
        $("#password").val("");
        $("#confirm_password").val("");
    });
}

function initResetPasswordPasswordShowers()
{
    new PasswordShower(
        "reset-password-icon",
        "password",
        "bi bi-eye-slash-fill",
        "bi bi-eye-fill"
    );

    new PasswordShower(
        "reset-confirm-password-icon",
        "confirm_password",
        "bi bi-eye-slash-fill",
        "bi bi-eye-fill"
    );
}

/* Write Code for Front-End Reset Password Form Validation and AJAX Sending */
function validateAndSubmitResetPasswordForm()
{
    // Validate the password input
    $("#password").on({
        input: () => passwordValidate("password"),
        focus: () => formControlFocusValidate("password"),
        blur: () => formControlBlurValidate("password")
    });

    // Validate the confirm password input
    $("#confirm_password").on({
        input: () => {
            passwordValidate("confirm_password");
            confirmPasswordValidate("confirm_password", "password");
        },
        focus: () => {
            formControlFocusValidate("password");
            formControlBlurValidate("confirm_password");
        },
        blur: () => {
            formControlBlurValidate("password");
            formControlBlurValidate("confirm_password");
        }
    });

    // Add an event listener for the reset-password button
    $("#reset-password-form").on("submit", function(e) {
        e.preventDefault();

        // Control whether to do the AJAX request or not
        var doAjax = false;

        // Check password and confirm_password input have the is-valid class
        if ($("#password").hasClass("is-valid") && $("#confirm_password").hasClass("is-valid"))
        {
            doAjax = true;
        }

        else
        {
            doAjax = false;

            if ($("#form-alerts-container").children().length() < 1)
            {
                displayFormErrorAlert("form-alerts-container", "Enter a new valid password");
            }
        }

        // Perform AJAX request
        if (doAjax)
        {
            // Grab the email for the user who needs a password change
            const email = $("#spring-user-email").val();

            // Grab actual password values
            const password = $("#password").val();
            const confirmPassword = $("#confirm_password").val();

            // Form JSON string to send to the back-end
            const data = JSON.stringify({
                email,
                password
            });

            // Grab original content from submit button
            const resetPasswordBtnHTMLOri = $("#reset-password").html();

            // Send AJAX Request
            $.ajax({
                url: `${websiteURL}/reset-password`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    // Disable Button to avoid request before response
                    const resetPasswordBtn = $("#reset-password");
                    resetPasswordBtn.html(loadingSpinner());
                    resetPasswordBtn.prop("disabled", true);
                },
                success: function(response) {
                    displayFormSuccessAlert("form-alerts-container", response["success"]);
                    $("#key-link").prop("href", "/login");
                    $("#key-link").text("Go to Login Page");
                },
                error: function(xhr) {
                    displayFormErrorAlert("form-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function() {
                    // Enable Reset Password Button
                    const resetPasswordBtn = $("#reset-password");
                    resetPasswordBtn.html(resetPasswordBtnHTMLOri);
                    resetPasswordBtn.prop("disabled", false);
                }
            });
        }
    });
}

$(document).ready(mainResetPassword);