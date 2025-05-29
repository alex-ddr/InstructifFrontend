// Instance de la carte
let googleMapInstance;
const markers = [];

// Exemple de données d'établissements
// À remplacer par un appel AJAX pour récupérer depuis ta BDD
const etablissements = [
  {
    id: 1,
    nom: "INSA Lyon",
    lat: 45.782122,
    lng: 4.872735,
    ips: 12.3,
    nbSoutien: 5,
    dureeMoyenne: "1h30"
  },
  {
    id: 2,
    nom: "Université Lyon 2",
    lat: 45.760142,
    lng: 4.854177,
    ips: 14.1,
    nbSoutien: 3,
    dureeMoyenne: "2h00"
  }
  // … autres établissements
];

function initMap() {
  // Centre de la carte (premier établissement ou centre par défaut)
  const centre = etablissements.length
    ? { lat: etablissements[0].lat, lng: etablissements[0].lng }
    : { lat: 46.0, lng: 2.0 };

  googleMapInstance = new google.maps.Map(document.getElementById("map"), {
    center: centre,
    zoom: 12,
  });

  // Création des marqueurs
  etablissements.forEach((etab) => {
    const marker = new google.maps.Marker({
      position: { lat: etab.lat, lng: etab.lng },
      map: googleMapInstance,
      title: etab.nom,
    });
    markers.push({ marker, data: etab });

    // Quand on clique sur un marqueur, on met à jour la info-box
    marker.addListener("click", () => afficherInfoEtablissement(etab));
  });

  // Mettre à jour également les statistiques globales
  document.getElementById("nb-soutiens").textContent =
    etablissements.reduce((sum, e) => sum + e.nbSoutien, 0);
  document.getElementById("ips").textContent =
    (etablissements.reduce((sum, e) => sum + e.ips, 0) /
      etablissements.length
    ).toFixed(1);
}

function afficherInfoEtablissement(etab) {
  document.getElementById("etab").textContent = etab.nom;
  document.getElementById("etab-ips").textContent = etab.ips;
  document.getElementById("etab-soutiens").textContent = etab.nbSoutien;
  document.getElementById("etab-duree").textContent = etab.dureeMoyenne;
}