"use strict";

function mainProfile()
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
            `${webkitURL}/dashboard/profile/avatarUpdate`
        );
    });
}

$(document).ready(mainProfile);