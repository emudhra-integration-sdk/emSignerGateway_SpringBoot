$(document).ready(function () {
  fetchAndSetParameters();
});

function fetchAndSetParameters() {
  $.ajax({
    url: "api/getEmSignerParams",
    type: "POST",
    data: JSON.stringify(req),
    contentType: "application/json",
    success: function (response) {
      response = JSON.parse(response);
      if (response.Status) {
        $("#Parameter1").val(response.Parameter1);
        $("#Parameter2").val(response.Parameter2);
        $("#Parameter3").val(response.Parameter3);
        $("#SessionKey").val(response.SessionKey);
      }
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
    },
  });
}
