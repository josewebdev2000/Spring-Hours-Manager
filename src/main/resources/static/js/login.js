"use strict";

function mainLogin()
{
    // Initialize Password Shower
    initLoginPasswordShower();

    // Reset login form on reload
    resetLoginForm();

    // validate and submit login form
    validateAndSubmitLoginForm();
}

function initLoginPasswordShower()
{
    new PasswordShower(
        "login-password-icon",
        "password",
        "bi bi-eye-slash-fill",
        "bi bi-eye-fill"
    );
}

function resetLoginForm()
{
    $(window).on("beforeunload", function() {
        $("#email").val("");
        $("#password").val("");
    });
}

/** Write Code for Front-End Login Form Validation and AJAX Sending */
function validateAndSubmitLoginForm()
{
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

    // Handle form submission
    $("#login-form").on("submit", function (e) {

        // Control whether AJAX request should be done or not
        var doAjax = false;

        // Make sure default form submission is disabled
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
            const email = $("#email").val();
            const password = $("#password").val();

            // Form JSON string to send to the back-end
            const data = JSON.stringify({
                email,
                password
            });

            // Grab original content from submit button
            const loginBtnHTMLOri = $("#login").html();

            // Send AJAX Request
            $.ajax({
                url: `${websiteURL}/login`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function () {
                    // Disable Button to avoid request before response
                    const loginBtn = $("#login");
                    loginBtn.html(loadingSpinner());
                    loginBtn.prop("disabled", true);
                },
                success: function (response) {
                    // Send the user to the dashboard
                    location.replace(response["redirectUrl"]);
                },
                error: function (xhr) {
                    displayFormErrorAlert("form-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function () {
                    // Enable Button to Allow Requests Once Response Has Been Processed
                    const loginBtn = $("#login");
                    loginBtn.html(loginBtnHTMLOri);
                    loginBtn.prop("disabled", false);
                }
            });
        }
    });
}

$(document).ready(mainLogin);