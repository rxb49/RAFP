function confirmDeleteEmployeur(path, employeur, id_emp) {
    const urlParams = new URLSearchParams(window.location.search);
    var noInsee = urlParams.get("no_insee");
    var userConfirmed = confirm("Voulez-vous vraiment supprimer " + employeur + " pour l'agent " + noInsee + "?");

    if (userConfirmed) {
        fetch(path+'/ajoutEmployeur/delete', {
            method: 'POST',
            body: JSON.stringify({
                noInsee: noInsee,
                idEmployeur: id_emp
            }),
            headers: {
                "Content-type":
                    "application/json"
            }
        })
            .then(response => response.text())
            .then(text => {
                if(text === ""){
                    Swal.fire({
                        icon: "success",
                        title: "Le retour est supprimé",
                    }).then(() => {
                        location.reload();
                    });
                }else{
                    Swal.fire({
                        icon: "error",
                        title: text,
                    }).then(() => {
                        location.reload();
                    });
                }

            })
            .catch(error => {
                Swal.fire({
                    icon: "error",
                    title: 'Erreur lors de la requête :' + error,
                }).then(() => {
                    location.reload();
                });
            });

    } else {
        console.log(employeur + " n'a pas été supprimé.");
    }


}

function confirmDeleteAgent(agentInsee) {
    if (confirm("Voulez-vous vraiment supprimer l'agent avec l'INSEE " + agentInsee + " ?")) {
        console.log("Agent " + agentInsee + " supprimé.");
    }
}