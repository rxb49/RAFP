function getInfoAgent(path) {
    var selectElement = document.getElementById("idAgents");
    var idAgent = selectElement.value;
    var selectedOption = selectElement.options[selectElement.selectedIndex];
    var nomUsuel = selectedOption.getAttribute("data-nom");
    var prenom = selectedOption.getAttribute("data-prenom");

    fetch(path + '/donneesAgent/getInfo', {
        method: 'POST',
        body: JSON.stringify({ idAgent: idAgent }),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                // Vérifie si la réponse est un succès
                return response.text().then(text => {
                    throw new Error("Erreur serveur: " + text);
                });
            }
            return response.text().then(text => {
                if (text) {
                    return JSON.parse(text);  // Si la réponse est non vide, on parse le JSON
                }
                throw new Error("Réponse vide du serveur");
            });
        })
        .then(agent => {
            console.log(agent);  // Afficher les informations de l'agent dans la console

            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";  // Vider le tableau avant d'ajouter les nouvelles données

            // Créer une nouvelle ligne pour afficher les informations de l'agent
            var row = document.createElement("tr");

            // Ajouter les informations de l'agent dans les cellules du tableau
            var cellNom = document.createElement("td");
            cellNom.textContent = nomUsuel || "Non disponible";
            row.appendChild(cellNom);

            var cellPrenom = document.createElement("td");
            cellPrenom.textContent = prenom || "Non disponible";
            row.appendChild(cellPrenom);

            var cellTBI = document.createElement("td");
            cellTBI.textContent = agent.tbi + " €" || "Non disponible";
            row.appendChild(cellTBI);

            var cellIndemn = document.createElement("td");
            cellIndemn.textContent = agent.indemn + " €" || "Non disponible";
            row.appendChild(cellIndemn);

            var cellSeuil = document.createElement("td");
            cellSeuil.textContent = agent.seuil + " €" || "Non disponible";
            row.appendChild(cellSeuil);

            var cellBaseRestante = document.createElement("td");
            cellBaseRestante.textContent = agent.base_Restante + " €" || "Non disponible";
            row.appendChild(cellBaseRestante);

            var cellMontantRetour = document.createElement("td");
            cellMontantRetour.textContent = agent.total_Retour + " €" || "Non disponible";
            row.appendChild(cellMontantRetour);

            var cellBaseRetourRecalcule = document.createElement("td");
            cellBaseRetourRecalcule.textContent = agent.base_Retour_Recalculee + " €" || "Non disponible";
            row.appendChild(cellBaseRetourRecalcule);

            // Ajouter la ligne au tableau
            tbody.appendChild(row);
        })
        .catch(error => {
            console.error("Erreur lors de la requête :", error);
            // Affichage d'un message d'erreur si nécessaire, par exemple
            alert("Une erreur est survenue lors de la récupération des informations de l'agent.");
        });
}