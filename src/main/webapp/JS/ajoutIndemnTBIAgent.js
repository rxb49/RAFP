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
        .then(response => response.text().then(text => ({ status: response.status, text })))
        .then(({ status, text }) => {
            if (status === 200) {
                Swal.fire({
                    icon: "success",
                    title: "Succès",
                    text: text,
                }).then(() => {
                    fetchTempData(path);
                });
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Erreur",
                    text: text,
                }).then(() => {
                    location.reload();
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
