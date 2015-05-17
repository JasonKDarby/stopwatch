$(document).ready(function() {

  var localStart  = {},
      remoteStart = {},
      localStop   = {},
      remoteStop  = {};

  function start() {
    localStart = new Date()
    $("#startTime").html(localStart.toISOString())

    $.post("/api", function(data) {
      remoteStart = data
    })
  }

  function stop() {
    $("#time-data").show()

    localStop = new Date()
    $("<tr><td>local</td><td>"+localStart.toISOString()+"</td><td>"+localStop.toISOString()+"</td><td>"+((localStop-localStart)/1000)+"</td></tr>").prependTo("#time-data > tbody")

    $.post("/api/"+remoteStart.id, function(data) {
      remoteStop = data
      $("<tr><td>remote</td><td>"+remoteStop.startTime+"</td><td>"+remoteStop.endTime+"</td><td>"+(remoteStop.duration/1000)+"</td></tr>").prependTo("#time-data > tbody")
    })
  }

  $("#startButton").click(start)
  $("#stopButton").click(stop)
  $("#time-data").hide()
})
