"use strict";

function mainProfile()
{
    // Operations to change the username
    updateProfileUsername();

    // Operations to change the profile picture
    updateProfilePicOperations();

    // Operations to change the password
    updateProfilePassword();
}
function updateProfileUsername()
{
    // Detect if the Username Form is shown
    const usernameEditor = $("#username-editor");

    if (usernameEditor.hasClass("active"))
    {
        // Grab relevant elements
        resetUsernameChangingForm();

        // Validate and Submit the Username Changing Form
        validateAndSubmitUsernameChangingForm();

    }
}

function updateProfilePicOperations()
{
    // Detect when the Profile Picture button is clicked
    const profilePicBtn = $("a[href='#profile-pic-editor']");

    profilePicBtn.on("click", function () {

        // Grab relevant ids
        const picsGridSelectorId = "pic-grid-selector";
        const inputUrlId = "avatar-url";
        const commonPicClass = "avatar";
        const avatarButtonId = "avatar-btn";

        // Initialize a Pic Grid Selector
        const avatarPicSelector = new PicGridSelector(
            picsGridSelectorId,
            inputUrlId,
            commonPicClass,
            avatarButtonId
        );

        // Send the Picture to the Backend when required
        avatarPicSelector.sendPicUrlToBackend(
            avatarPicSelector,
            `${websiteURL}/dashboard/profile/avatarUpdate`,
            () => {
                const formAlertsContainer = $("#profile-pic-alerts-container");

                if (formAlertsContainer.children().length < 1)
                {
                    displayFormErrorAlert("profile-pic-alerts-container", "Choose a picture from the palette");
                }
            },
            () => {
                // Disable Avatar Button to avoid requests before response
                const avatarBtn = $("#avatar-btn");
                avatarBtn.prop("disabled", true);
            },
            (response) => {
                // Send the user to the profile again
                location.replace(response["redirectUrl"]);
            },
            (xhr) => {
                displayFormErrorAlert("profile-pic-alerts-container", xhr.responseJSON["error"]);
            },
            () => {
                // Enable Avatar Button again
                const avatarBtn = $("#avatar-btn");
                avatarBtn.prop("disabled", false);
            }
        );
    });
}

function updateProfilePassword()
{
    // Detect When the Password Editor Pane is Clicked
    const passwordEditorBtn = $("a[href='#password-editor']");

    passwordEditorBtn.on("click", function() {
        resetPasswordChangingForm();

        validateAndSubmitPasswordChangingForm();
    });

}

function resetUsernameChangingForm()
{
    $(window).on("beforeunload", function() {
        $("#email").val("");
    });
}

function resetPasswordChangingForm()
{
    $(window).on("beforeunload", function() {
        $("#password").val("");
    });
}

function validateAndSubmitUsernameChangingForm()
{
    // Validate the username input
    $("#username").on({
        input: () => usernameValidate("username"),
        focus: () => formControlFocusValidate("username"),
        blur: () => formControlBlurValidate("username")
    });

    // Handle form submission
    $("#username-form").on("submit", function(e) {
        // Control whether the AJAX request should be done or not
        var doAjax = false;

        // Make sure default form submission is disabled
        e.preventDefault();

        // The username form control is valid
        if($("#username").hasClass("is-valid") && $("#confirm-username").prop("checked"))
        {
            doAjax = true;
        }

        else
        {
            doAjax = false;

            if ($("#profile-username-alerts-container").children().length < 1)
            {
                displayFormErrorAlert("profile-username-alerts-container", "Enter a valid username and confirm your choice");
            }
        }

        // Perform AJAX request if needed
        if (doAjax)
        {
            // Grab actual values from username control
            const username = $("#username").val();

            // Form JSON string to send to the back-end
            const data = JSON.stringify({
                username
            });

            // Send AJAX Request
            $.ajax({
                url: `${websiteURL}/dashboard/profile/usernameUpdate`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    // Disable Button to avoid request before response
                    const usernameButton = $("#username-btn");
                    usernameButton.prop("disabled", true);
                },
                success: function(response) {
                    // Send the user to the dashboard
                    location.replace(response["redirectUrl"]);
                },
                error: function(xhr)
                {
                    displayFormErrorAlert("profile-username-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function() {
                    // Enable Button to avoid request before response
                    const usernameButton = $("#username-btn");
                    usernameButton.prop("disabled", false);
                }
            });
        }
    });
}

function validateAndSubmitPasswordChangingForm()
{
    // Validate the password input
    $("#password").on({
        input: () => passwordValidate("password"),
        focus: () => formControlFocusValidate("password"),
        blur: () => formControlBlurValidate("password")
    });

    // Handle form submission
    $("#password-form").on("submit", function(e) {
        // Control whether AJAX request should be made
        var doAjax = false;

        // If the password is valid and the checkbox is checked do AJAX request
        e.preventDefault();

        if ($("#password").hasClass("is-valid") && $("#confirm-password").prop("checked"))
        {
            doAjax = true;
        }

        else
        {
            doAjax = false;
            displayFormErrorAlert("profile-password-alerts-container", "Enter a valid password and confirm your choice")
        }

        // Perform AJAX request if needed
        if (doAjax)
        {
            // Grab password value
            const password = $("#password").val();

            // Form the JSON string to send to the back-end
            const data = JSON.stringify({
                password
            });

            // Send the AJAX request
            $.ajax({
                url: `${websiteURL}/dashboard/profile/passwordUpdate`,
                method: "POST",
                contentType: "application/json",
                data,
                beforeSend: function() {
                    // Disable Btn to avoid new requests
                    const passwordBtn = $("#password-btn");
                    passwordBtn.prop("disabled", true);
                },
                success: function(response) {
                    displayFormSuccessAlert("profile-password-alerts-container", response["success"]);
                },
                error: function(xhr) {
                    displayFormErrorAlert("profile-password-alerts-container", xhr.responseJSON["error"]);
                },
                complete: function() {
                    // Enable Btn to allow new requests
                    const passwordBtn = $("#password-btn");
                    passwordBtn.prop("disabled", false);
                }
            });
        }
    });
}

$(document).ready(mainProfile);