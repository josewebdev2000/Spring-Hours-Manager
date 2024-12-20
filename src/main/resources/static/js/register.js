"use strict";

function mainRegister()
{
    // Initialize Password Showers for this page
    initRegisterPasswordShowers();

    // Reset register form on reload
    resetRegisterForm();

    // Validate and submit register form
    validateAndSubmitRegisterForm();
}

function initRegisterPasswordShowers()
{
    new PasswordShower(
        "register-password-icon",
        "password",
        "bi bi-eye-slash-fill",
        "bi bi-eye-fill"
    );

    new PasswordShower(
        "register-confirm-password-icon",
        "confirm_password",
        "bi bi-eye-slash-fill",
        "bi bi-eye-fill"
    );
}

function resetRegisterForm()
{
    $(window).on("beforeunload", function () {
        $("#email").val("");
        $("#name").val("");
        $("#password").val("");
        $("#confirm_password").val("");
    });
}

/** Write Code for Front-End Register Form Validation and AJAX Sending */
function validateAndSubmitRegisterForm()
{
    // Validate the name input
    $("#name").on({
        input: () => nameValidate("name"),
        focus: () => formControlFocusValidate("name"),
        blur: () => formControlBlurValidate("name")
    });

    // Validate the email input
    $("#email").on({
        input: () => emailValidate("email"),
        focus: () => formControlFocusValidate("email"),
        blur: () => formControlBlurValidate("email")
    });

    // Validate the password input
    $("#password").on({
        input: () => passwordValidate("password"),
        focus: () => formControlFocusValidate("password"),
        blur: () => formControlBlurValidate("password")
    });

    // Validate the confirmation password input as well
    $("#confirm_password").on({
        input: () => {
            passwordValidate("confirm_password");
            confirmPasswordValidate("confirm_password", "password");
        },
        focus: () => {
            formControlFocusValidate("password");
            formControlFocusValidate("confirm_password");
        },
        blur: () => {
            formControlBlurValidate("password");
            formControlBlurValidate("confirm_password");
        }
    });

    // Submit the form
    $("#register-form").on("submit", function (e) {

        // Control whether AJAX request should be done or not
        var doAjax = false;

        // Make sure default form submission is canceled
        e.preventDefault();


        // Check all required form controls have the is-valid class
        const formControls = $(".form-control:not(.note-input)").toArray();

        for (let formControl of formControls)
        {
            if ($(formControl).hasClass("is-valid"))
            {
                doAjax = true;
            }

            else
            {
                doAjax = false;

                if ($("#form-alerts-container").children().length < 1)
                {
                    displayFormErrorAlert("form-alerts-container", "Fill the form correctly");
                }

                break;
            }
        }

        // Perform AJAX request if needed
        if (doAjax)
        {
            // Grab actual values from form controls
            const name = $("#name").val();
            const email = $("#email").val();
            const password = $("#password").val();

            // Form JSON string to send to the back-end
            const data = JSON.stringify({
                name,
                email,
                password
            });

            // Grab original content from submit button
            const createAccountBtnHTMLOri = $("#create-account").html();

            // Send AJAX Request
            $.ajax({
                url: `${websiteURL}/register`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    // Disable Button to Avoid Request Before Response
                    const createAccountBtn = $("#create-account");
                    createAccountBtn.html(loadingSpinner());
                    createAccountBtn.prop("disabled", true);
                },
                success: function (response) {
                    // Send the user to the dashboard
                    location.replace(response["redirectUrl"]);
                },
                error: function(xhr) {
                    displayFormErrorAlert("form-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function() {
                    // Enable Button to Allow Request After Response Has Been Processed
                    const createAccountBtn = $("#create-account");
                    createAccountBtn.html(createAccountBtnHTMLOri);
                    createAccountBtn.prop("disabled", false);
                }

            });
        }
    });
}

$(document).ready(mainRegister);