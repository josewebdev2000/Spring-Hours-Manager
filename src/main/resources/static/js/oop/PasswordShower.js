/* Allow a Jquery Object to switch between showing passwords and hiding them */
class PasswordShower
{
    constructor(icon_id, input_id, show_password_class, hide_password_class)
    {
        // Refer self to this
        const self = this;

        // Select both the icon and the input using jQuery
        self.icon = $(`#${icon_id}`);
        self.input = $(`#${input_id}`);

        // Save the classes for showing or hiding passwords
        self.show_password_class = show_password_class;
        self.hide_password_class = hide_password_class;

        // Handle all interactions as soon as the object is created
        self.icon_handle(self, self.icon, self.input);
    }

    icon_handle(self, icon_jquery_obj, input_jquery_obj)
    {
        /* Handle all interactions with the icon */
        icon_jquery_obj
            .css({
                cursor: "pointer"
            })
            .on("mouseenter", function () {
                // Reduce opacity a bit
                icon_jquery_obj.animate({
                    opacity: 0.6
                }, "slow");
            })
            .on("mouseleave", function() {
                // Set opacity back to normal

                icon_jquery_obj.animate({
                    opacity: 1
                }, "slow");
            })
            .on("click", function() {
                // Change the icon depending on what the user clicked
                if (icon_jquery_obj.attr("class").includes(self.show_password_class))
                {
                    // Hide Password
                    icon_jquery_obj.removeClass(self.show_password_class).addClass(self.hide_password_class);
                    input_jquery_obj.prop("type", "password");
                }

                else
                {
                    // Show Password
                    icon_jquery_obj.removeClass(self.hide_password_class).addClass(self.show_password_class);
                    input_jquery_obj.prop("type", "text");
                }
            });
    }
}