function confirmDeleteEmployeur(agent) {
    alert("Voulez-vous vraiment supprimer " + agent + " ?");
}

function confirmDeleteAgent(agentInsee) {
    if (confirm("Voulez-vous vraiment supprimer l'agent avec l'INSEE " + agentInsee + " ?")) {
        console.log("Agent " + agentInsee + " supprimé.");
    }
}