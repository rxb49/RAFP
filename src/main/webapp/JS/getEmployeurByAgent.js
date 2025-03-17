function showInfo(path){
    getInfoAgent(path);
    getEmployeur(path);
}

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
                return response.text().then(text => {
                    throw new Error("Erreur serveur: " + text);
                });
            }
            return response.text().then(text => {
                if (text) {
                    return JSON.parse(text);
                }
                throw new Error("Réponse vide du serveur");
            });
        })
        .then(agent => {
            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";

            var row = document.createElement("tr");

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

            tbody.appendChild(row);
        })
        .catch(error => {
            console.error("Erreur lors de la requête :", error);
            alert("Une erreur est survenue lors de la récupération des informations de l'agent.");
        });
}


function getEmployeur(path) {
    var selectElement = document.getElementById("idAgents");
    var idAgent = selectElement.value;
    fetch(path + '/donneesAgent/getEmployeur', {
        method: 'POST',
        body: JSON.stringify({ idAgent: idAgent }),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error("Erreur serveur: " + text);
                });
            }
            return response.text().then(text => {
                if (text) {
                    return JSON.parse(text);
                }
                throw new Error("Réponse vide du serveur");
            });
        })
        .then(agents => {

            var tbody = document.getElementById("employeursTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";  // Vider le tableau

            if (!Array.isArray(agents) || agents.length === 0) {
                var row = document.createElement("tr");
                var cell = document.createElement("td");
                cell.colSpan = 2;
                cell.textContent = "Aucun employeur trouvé";
                row.appendChild(cell);
                tbody.appendChild(row);
                return;
            }

            agents.forEach(agent => {
                var row = document.createElement("tr");

                var cellLibEmp = document.createElement("td");
                cellLibEmp.textContent = agent.lib_emp || "Non disponible";
                row.appendChild(cellLibEmp);

                var cellMontant = document.createElement("td");
                cellMontant.textContent = agent.mnt_retour ? agent.mnt_retour + " €" : "Non disponible";
                row.appendChild(cellMontant);


                var cellModifier = document.createElement("td");
                var linkModifier = document.createElement("a");
                linkModifier.href = path + "/modifierAgent";
                linkModifier.className = "text-primary";
                linkModifier.textContent = "Modifier";
                cellModifier.appendChild(linkModifier);
                row.appendChild(cellModifier);

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

                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Erreur lors de la requête :", error);
            alert("Une erreur est survenue lors de la récupération des informations des employeurs.");
        });
}