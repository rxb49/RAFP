let timeout;

document.addEventListener("DOMContentLoaded", function () {
    const inputSearch = document.getElementById("inputName");

    if (inputSearch) {
        inputSearch.addEventListener("input", function () {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                rechercherAgent(this.value);
            }, 300);
        });
    }
});

function rechercherAgent(valeur) {
    console.log("Recherche de l'agent :", valeur);
    getAgentBySearch(path);
}

function getAgentBySearch(path) {
    var inputSearch = document.getElementById("inputName").value.trim().toUpperCase();

    fetch(`${path}/rechercheAgent/search?recherche=${encodeURIComponent(inputSearch)}`, {
        method: 'GET',
        headers: {"Accept": "application/json"}
    })
        .then(response => response.json())
        .then(data => {
            console.log("Réponse du serveur :", data);

            if (!Array.isArray(data)) {
                console.error("Erreur : la réponse n'est pas un tableau", data);
                return;
            }

            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";

            data.forEach(agent => {
                var row = document.createElement("tr");

                var cellPrenom = document.createElement("td");
                cellPrenom.textContent = agent.prenom;
                row.appendChild(cellPrenom);

                var cellNom = document.createElement("td");
                cellNom.textContent = agent.nom_usuel || 'N/A';
                row.appendChild(cellNom);

                var cellNo_insee = document.createElement("td");
                cellNo_insee.textContent = agent.no_insee || 'N/A';
                row.appendChild(cellNo_insee);

                var cellVoir = document.createElement("td");

                var linkVoir = document.createElement("a");
                linkVoir.className = "text-primary";  // Applique une classe de style
                linkVoir.textContent = "Voir";  // Texte du lien
                var url = `${path}/donneesAgent/${agent.no_insee}`;
                linkVoir.href = url;


                cellVoir.appendChild(linkVoir);
                row.appendChild(cellVoir);

                tbody.appendChild(row);
            })
                .catch(error => console.error("Erreur lors de la requête :", error));
        })
}
