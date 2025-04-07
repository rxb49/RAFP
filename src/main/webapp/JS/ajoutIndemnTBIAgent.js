function ajoutIndemnTBIAgent(path) {
    let tbi = document.getElementById("tbi").value;
    let indemn = document.getElementById("indemn").value;

    const pathSegments = window.location.pathname.split('/');
    const noInsee = pathSegments[pathSegments.length - 1];

    fetch(path + '/modiferIndemnTBIAgent/modifier', {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ noInsee, tbi, indemn })
    })
        .then(response => response.text())
        .then(message => {
            if (message === "Les montants ont été ajoutés") {
                Swal.fire({
                    icon: "success",
                    title: message,
                    confirmButtonText: "OK"
                }).then(() => {
                    window.location.href = path + "/donneesAgent/" + noInsee;
                });
            } else {
                Swal.fire({
                    icon: "error",
                    title: message
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête : ' + error
            });
        });
}
