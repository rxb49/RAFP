function importTotal(path) {
    console.log("Passage dans importTotal");

    const fileInput = document.getElementById("exampleInputFile");
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
                    columns[0] = columns[0].substring(0, 13);  // Trim noInsee to 13 characters
                    columns[1] = columns[1].replace(",", ".");  // Convert amount to correct format
                    return columns;
                });

            // Préparation du tableau d'objets avec les champs requis
            const jsonData = data.map(item => ({
                noInsee: item[0].replace(",", "").trim(),
                montant: parseFloat(item[1].replace(",", ".")),  // S'assurer que c'est un nombre
                idEmployeur: parseInt(item[2].trim())
            }));

            console.log(jsonData);

            fetch(path + '/importTotal/insert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jsonData)
            })
                .then(response => response.text())
                .then(text => {
                    Swal.fire({
                        icon: "success",
                        title: "Les données ont été insérées dans la table temporaire.",
                    }).then(() => {
                        fetchTempData(path); // Charger les données dans la table temporaire
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: "error",
                        title: "Erreur lors de l'insertion : " + error,
                    });
                });
        };
        reader.readAsText(file);
    }
}

// Récupération et affichage des données en attente de validation
function fetchTempData(path) {
    console.log("passage dans fetchTempData");
    fetch(path + "/importTotal/tempData")
        .then(response => response.json())
        .then(data => {
            let html = "<table class='table table-striped'><tr><th>No INSEE</th><th>Nom</th><th>Prenom</th><th>Montant</th><th>Employeur</th><th>Id Employeur</th></tr>";
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
            document.getElementById("tempDataTable").innerHTML = html;
            document.getElementById("validateButton").style.display = "block"; // Afficher le bouton de validation
        })
        .catch(error => {
            console.error("Erreur lors de la récupération des données temporaires :", error);
        });
}

// Validation des données (insertion définitive)
function validateImport(path) {
    fetch(path + "/importTotal/validate", { method: 'POST' })
        .then(response => response.text())
        .then(text => {
            Swal.fire({
                icon: "success",
                title: "Données validées avec succès !"
            }).then(() => {
                location.reload(); // Recharger la page après validation
            });
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: "Erreur lors de la validation : " + error
            });
        });
}
