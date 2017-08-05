 $().ready(function () {
     $.validator.setDefaults({
         errorClass: 'help-block',
         highlight: function(element) {
             $(element)
                 .closest('.form-group')
                 .addClass('has-error');
         },
         unhighlight: function(element) {
             $(element)
                 .closest('.form-group')
                 .removeClass('has-error');
         }
     });

     $('#registerForm').validate({
         rules: {
             firstname: {
                 required : true,
                 minlength : 2
             },
             lastname: {
                 required : true,
                 minlength : 2
             },
             email: {
                 required: true,
                 email: true
             }
         },
         messages: {
             firstname: {
                 required : "Unesite svoje ime.",
                 minlength : "Ime mora imati bar 2 karaktera."
             },
             lastname: {
                 required : "Unesite svoje prezime.",
                 minlength : "Prezime mora imati bar 2 karaktera."
             },
             email: {
                 required: "Unesite svoj email.",
                 email: "Unesite validnu email adresu."
             }
         },
         submitHandler: function(form) {
             var uniques = chance.unique(chance.natural, 1, {min: 1000, max: 9999});
             var firstname = $("#firstname").val();
             var lastname = $("#lastname").val();
             var email = $("#email").val();
             var statusKorisnika = "allowed";

             var user = {
                 userID : uniques[0],
                 firstname : $("#firstname").val(),
                 lastname : $("#lastname").val(),
                 email : $("#email").val(),
                 statusKorisnika : "allowed"
             };

             $.ajax({
                 type: "POST",
                 url: "../api/index/post",
                 data: JSON.stringify(user),
                 contentType: "application/json; charset=utf-8",
                 dataType: "text",
                 success: function (data) {

                     if(data == true){
                         // BootstrapDialog.show({
                         //     title: 'Greska',
                         //     message: 'Uneta email adresa je vec regostrovana. Molimo Vas da unesete novu.'
                         // });
                         bootbox.alert({
                             message: "Uneta email adresa je vec regostrovana. Molimo Vas da unesete novu.",
                             size: 'small'
                         });
                     }else {
                         // BootstrapDialog.show({
                         //     title: 'Potvrda',
                         //     message: 'Uspesno ste se registrovali! Kod za besplatan kondom poslat Vam je na email.'
                         // });
                         bootbox.alert({
                             message: "Uspesno ste se registrovali! Kod za besplatan kondom poslat Vam je na email.",
                             size: 'small'
                         });
                     }
                 },
                 error: function (jqXHR, status) {
                     // error handler
                     console.log(jqXHR);
                     alert('fail' + status.code);
                 }
             });
             return false;
         }
     });

    //     $("#registerButton").click(function(e){
    //         e.preventDefault();
		// 	var firstname = $("#firstname").val();
		// 	var lastname = $("#lastname").val();
		// 	var email = $("#email").val();
		// 	var statusKorisnika = "allowed";
    //
    //         $.ajax({
    //             type: "POST",
    //             url: "/api/index",
    //             data: JSON.stringify({
    //                 "firstname" : $("#firstname").val(),
    //                 "lastname" : $("#lastname").val(),
    //                 "email" : $("#email").val(),
    //                 "statusKorisnika" : statusKorisnika
    //             }),
    //             contentType: "application/json; charset=utf-8",
    //             dataType: "json",
    //             success: function (data) {
    //
    //                 if(data == true){
    //                     BootstrapDialog.show({
    //                         title: 'Greska',
    //                         message: 'Uneta email adresa je vec regostrovana. Molimo Vas da unesete novu.'
    //                     });
    //                 }else {
    //                     BootstrapDialog.show({
    //                         title: 'Potvrda',
    //                         message: 'Uspesno ste se registrovali! Kod za besplatan kondom poslat Vam je na email.'
    //                     });
    //                 }
    //             },
    //             error: function (jqXHR, status) {
    //                 // error handler
    //                 console.log(jqXHR);
    //                 alert('fail' + status.code);
    //             }
    //         });
    // });
 });
 
