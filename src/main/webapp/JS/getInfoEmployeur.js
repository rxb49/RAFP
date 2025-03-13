
function updateEmployerDetails() {
    var select = document.getElementById("idEmployeurs");
    var selectedOption = select.options[select.selectedIndex];
    var name = selectedOption.getAttribute("data-name");
    var email = selectedOption.getAttribute("data-email");

    document.getElementById("nomEmployeurUpdate").value = name;
    document.getElementById("mailEmployeurUpdate").value = email;
}

function insertEmployeur(path){
    let nomEmployeur = document.getElementById("nomEmployeur").value;
    let mailEmployeur = document.getElementById("mailEmployeur").value;
    fetch(path+'/gestionEmployeur/add', {
        method: 'POST',
        body: JSON.stringify({
            mail_emp: mailEmployeur,
            lib_emp: nomEmployeur,
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
                    title: "L'employeur est ajouté",
                });
                appelModifier(path);
            }else{
                Swal.fire({
                    icon: "error",
                    title: text,
                });
            }
            document.getElementById('nomEmployeur').value = '';
            document.getElementById('mailEmployeur').value = '';
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}

function appelModifier(path){
    fetch(path+'/gestionEmployeur/modifier', {

    })
        .then(response => response.text())
        .then(text => {
            document.getElementById("cadre1").innerHTML = text;

        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}


function updateEmployeur(path){
    let idEmployeur = document.getElementById("idEmployeurs").value;
    let nomEmployeur = document.getElementById("nomEmployeurUpdate").value;
    let mailEmployeur = document.getElementById("mailEmployeurUpdate").value;
    fetch(path+'/gestionEmployeur/update', {
        method: 'POST',
        body: JSON.stringify({
            id_emp: idEmployeur,
            lib_emp: nomEmployeur,
            mail_emp: mailEmployeur
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
                    title: "L'employeur est modifié",
                });
                appelModifier(path);
            }else{
                Swal.fire({
                    icon: "error",
                    title: text,
                });
            }
            appelModifier(path);
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}

function getEmployeur(path) {
    console.log("Passage dans getEmployeur");
    const url = path + '/listeRafp/employeurs';
    console.log("URL:", url);
    fetch(url, {
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // data should be a List<RafpEmployeur>
            const select = document.getElementById('listeAgentEmployeur');
            select.innerHTML = ''; // Clear any existing options

            data.forEach(employeur => {
                const option = document.createElement('option');
                option.value = employeur.id_emp;
                option.text = `${employeur.lib_emp} - ${employeur.mail_emp}`;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.log("Erreur dans le fetch:", error);
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la récupération des employeurs : ' + error,
            });
        });
}


function getAgents(path) {
    console.log("Passage dans getAgents");
    const url = path + '/listeRafp/agents';
    console.log("URL:", url);
    fetch(url, {
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            const select = document.getElementById('listeAgentEmployeur');
            select.innerHTML = '';

            data.forEach(agent => {
                const option = document.createElement('option');
                option.value = agent.no_insee;
                option.text = `${agent.nom_usuel} - ${agent.prenom}`;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.log("Erreur dans le fetch:", error);
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la récupération des agents : ' + error,
            });
        });
}


function getAgentByEmployeur(path) {
    console.log("Passage dans getAgentByEmployeur");
    const url = path + '/listeRafp/agentByEmployeur';
    console.log("URL:", url);
    fetch(url, {
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            const select = document.getElementById('listeAgentEmployeur');
            select.innerHTML = '';

            data.forEach(agent => {
                const option = document.createElement('option');
                option.value = agent.no_insee;
                option.text = `${agent.no_dossier_pers} - ${agent.tbi}`;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.log("Erreur dans le fetch:", error);
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la récupération des agents : ' + error,
            });
        });
}


