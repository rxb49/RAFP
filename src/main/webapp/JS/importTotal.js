document.addEventListener("DOMContentLoaded", function() {
    let path = document.getElementById("pageData").getAttribute("data-path");
    fetchTempData(path);
});
function importTotal(path) {
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const content = e.target.result;
            const lines = content.split("\n");

            const data = lines
                .filter(line => line.trim() !== '')
                .map(line => {
                    const columns = line.split(";");
                    columns[0] = columns[0].substring(0, 13);
                    columns[1] = columns[1].replace(",", ".");
                    return columns;
                });

            const jsonData = data.map(item => ({
                noInsee: item[0].replace(",", "").trim(),
                montant: parseFloat(item[1].replace(",", ".")),
                idEmployeur: parseInt(item[2].trim())
            }));

            let loader = document.getElementById("chargement");
            loader.classList.add("active");

            fetch(path + '/importTotal/insert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jsonData)
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
                .finally(() => {
                    loader.classList.remove("active");
                });
        };
        reader.readAsText(file);
    } else {
        Swal.fire({
            icon: "warning",
            title: "Veuillez sélectionner un fichier",
        });
    }
}


function fetchTempData(path) {

    fetch(path + "/importTotal/tempData")
        .then(response => response.json())
        .then(data => {
            let tempDataTable = document.getElementById("tempDataTable");
            let importForm = document.getElementById("importForm");
            let cancelButton = document.getElementById("cancelButton");

            if (data.length > 0) {
                let html = "<table class='table table-striped'><tr><th>No INSEE</th><th>Nom</th><th>Prénom</th><th>Montant</th><th>Employeur</th><th>ID Employeur</th></tr>";
                data.forEach(row => {
                    html += `<tr>
                        <td>${row.insee}</td>
                        <td>${row.nom_usuel}</td>
                        <td>${row.prenom}</td>
                        <td>${row.retour}</td>
                        <td>${row.lib_emp}</td>
                        <td>${row.id_emp}</td>
                    </tr>`;
                });
                html += "</table>";

                tempDataTable.innerHTML = html;
                document.getElementById("validateButton").style.display = "block";
                importForm.style.display = "none";
                cancelButton.style.display = "block";
            } else {
                tempDataTable.innerHTML = "<p>Aucune donnée en attente.</p>";
                document.getElementById("validateButton").style.display = "none";
                importForm.style.display = "block";
                cancelButton.style.display = "none";
            }
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: "Erreur lors de la récuperation des données temporaires : " + error,
            });
        });
}

function clearTempData(path) {
    fetch(path + "/importTotal/clearTempData", { method: "DELETE" })
        .then(response => response.text())
        .then(message => {
            Swal.fire({
                icon: "success",
                title: message,
            }).then(() => {
                fetchTempData(path);
            });
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: "Erreur lors de la suppression des données : " + error,
            });
        });
}

function validateImport(path) {
    let loader = document.getElementById("chargement");
    loader.classList.add("active");
    fetch(path + "/importTotal/validate", {
        method: "POST",
    })
        .then(response => response.text().then(text => ({ status: response.status, text })))
        .then(({ status, text }) => {
            if (status === 200) {
                Swal.fire({
                    icon: "success",
                    title: "Succès",
                    text: text,
                }).then(() => {
                    location.reload();
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
                title: "Erreur",
                text: "Une erreur est survenue lors de la validation.",
            });
        })
        .finally(() => {
            loader.classList.remove("active");
        });
}

