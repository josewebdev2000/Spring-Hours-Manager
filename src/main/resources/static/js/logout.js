"use strict";

function mainLogout()
{
    // Grab all logout buttons by the 'logout-btn' class
    const logoutBtns = $(".logout-btn");

    // Grab all logout forms
    const logoutForms = $(".logout-form");

    // Add a click event listener to it to logout
    logoutBtns.on("click", (e) => sendPostLogoutRequest(e, logoutForms));
}

function sendPostLogoutRequest(e, formSelector)
{
    e.preventDefault();
    // Trigger form submission
    formSelector.trigger("submit");

}

$(document).ready(mainLogout);