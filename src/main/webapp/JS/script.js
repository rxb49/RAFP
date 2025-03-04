//appel AJAX avec passage de json
function updatePassword(actuel, nouveau, confirm){
    $.ajax({
        type: 'POST',
        url: 'updatePasswordPersonnel',
        contentType: "application/json",
        dataType: "html",
        data:
            JSON.stringify({ 'actuel': actuel, 'nouveau': nouveau, 'confirm': confirm}),
    })
        .done(function(){
            swal("Mot de passe modifié.", {
                icon: "success",
            });
        })
        .fail(function(){
            swal("Erreur, merci de réessayer.", {
                icon: "error",
            });
        });
}

