let googleMapInstance;
const markers = [];

let etablissements = [];

$(document).ready(function () {
  $.ajax({
    type: 'POST',
    url: './ActionServlet',
    data: {
      todo: "lister-etablissements"
    },
    dataType: "json"
  })
    .done(function (data) {
      etablissements = data;
      console.log("Établissements chargés :", etablissements);
      initMap();
    })
    .fail(function (err) {
      console.error("Erreur AJAX :", err);
      alert("Erreur lors du chargement des établissements.");
    });
});


function initMap() {
  const centre = etablissements.length
    ? { lat: etablissements[0].coords.lat, lng: etablissements[0].coords.lng }
    : { lat: 46.0, lng: 2.0 };

  googleMapInstance = new google.maps.Map(document.getElementById("map"), {
    center: centre,
    zoom: 12,
  });

  etablissements.forEach((etab) => {
    const marker = new google.maps.Marker({
      position: { lat: etab.coords.lat, lng: etab.coords.lng },
      map: googleMapInstance,
      title: etab.nom,
    });
    markers.push({ marker, data: etab });

    marker.addListener("click", () => afficherInfoEtablissement(etab));
  });

  document.getElementById("nb-soutiens").textContent =
    etablissements.reduce((sum, e) => sum + e.nbSoutien, 0);

  document.getElementById("ips").textContent =
    (etablissements.reduce((sum, e) => sum + e.ips, 0) / etablissements.length).toFixed(1);
}


function afficherInfoEtablissement(etab) {
  document.getElementById("etab").textContent = etab.nom;
  document.getElementById("etab-ips").textContent = etab.ips;
  document.getElementById("etab-soutiens").textContent = etab.nbSoutien;
  document.getElementById("etab-duree").textContent = etab.dureeMoyenne;
}