yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    meta(charset:'utf-8')
    title("Stopwatch")

    meta(name: 'apple-mobile-web-app-title', content: 'Stopwatch')
    meta(name: 'description', content: '')
    meta(name: 'viewport', content: 'width=device-width, initial-scale=1')

    script(src: 'http://code.jquery.com/jquery-latest.min.js') { yield '' }

    link(href: '/images/favicon.ico', rel: 'shortcut icon')
    link(href: 'http://yui.yahooapis.com/pure/0.6.0/pure-min.css', rel: 'stylesheet')
    link(href: '/scripts/stopwatch.css', rel: 'stylesheet')
  }
  body {

    div(class: 'pure-g') {
      div(class: 'pure-u-1-4', '')
      button(class: 'pure-u-1-4 pure-button', id: 'startButton', 'Start')
      button(class: 'pure-u-1-4 pure-button', id: 'stopButton', 'Stop')
      div(class: 'pure-u-1-4', '')
    }

    div(class: 'pure-g') {
      div(class: 'pure-u-1-4', '')
      div(class: 'pure-u-1-4', 'start time')
      div(class: 'pure-u-1-4', id: 'startTime', '')
      div(class: 'pure-u-1-4', '')
    }

    table(class: 'pure-table', id: 'time-data') {
      thead {
        tr {
          th('record source')
          th('start')
          th('end')
          th('duration')
        }
      }
      tbody {

      }
    }

    script(src: '/scripts/stopwatch.js', type: 'text/javascript') { yield '' }

  }
}
