function getAgentByEmployeur(path) {
    var selectElement = document.getElementById("idEmployeurs");
    var idEmployeur = selectElement.value;
    var totalMontantRetourDisplay = document.getElementById("totalMontantRetour");

    fetch(path + '/donneesEmployeur/getAgents', {
        method: 'POST',
        body: JSON.stringify({idEmployeur: idEmployeur}),
        headers: {"Content-type": "application/json"}
    })
        .then(response => response.json())
        .then(data => {
            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";
            let totalMontant = 0;

            data.forEach(agent => {
                var row = document.createElement("tr");

                // 1️⃣ N° INSEE
                var cellInsee = document.createElement("td");
                cellInsee.textContent = agent.insee;
                row.appendChild(cellInsee);

                // 2️⃣ Nom
                var cellNom = document.createElement("td");
                cellNom.textContent = agent.nom_usuel || 'N/A';
                row.appendChild(cellNom);

                // 3️⃣ Prénom
                var cellPrenom = document.createElement("td");
                cellPrenom.textContent = agent.prenom || 'N/A';
                row.appendChild(cellPrenom);

                // 4️⃣ Montant retour total
                var cellMontant = document.createElement("td");
                cellMontant.textContent = agent.mnt_retour + " €";
                row.appendChild(cellMontant);

                // 5️⃣ Modifier
                var cellModifier = document.createElement("td");
                var linkModifier = document.createElement("a");
                linkModifier.href = path + "/modifierAgent";
                linkModifier.className = "text-primary";
                linkModifier.textContent = "Modifier";
                cellModifier.appendChild(linkModifier);
                row.appendChild(cellModifier);

                // 6️⃣ Supprimer
                var cellSupprimer = document.createElement("td");
                var linkSupprimer = document.createElement("a");
                linkSupprimer.href = "#";
                linkSupprimer.className = "text-danger";
                linkSupprimer.textContent = "Supprimer";
                linkSupprimer.onclick = function () {
                    confirmDelete(agent.insee);
                };
                cellSupprimer.appendChild(linkSupprimer);
                row.appendChild(cellSupprimer);

                // Ajouter la ligne au tableau
                tbody.appendChild(row);

                // Ajouter au total du montant retour
                totalMontant += parseFloat(agent.mnt_retour) || 0;
            });

            // Affichage du montant total
            totalMontantRetourDisplay.textContent = "Montant retour total: " + totalMontant.toFixed(2) + "€";
        })
        .catch(error => {
            console.error("Erreur lors de la requête :", error);
        });
}
