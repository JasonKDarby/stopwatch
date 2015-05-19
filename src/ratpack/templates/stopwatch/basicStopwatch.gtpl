yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    meta(charset: 'utf-8')
    title('Stopwatch')

    meta(name: 'apple-mobile-web-app-title', content: 'Stopwatch')
    meta(name: 'description', content: '')
    meta(name: 'viewport', content: 'width=device-width, initial-scale=1')

    script(src: 'http://code.jquery.com/jquery-latest.min.js') { yield '' }

    link(href: '/images/favicon.ico', rel: 'shortcut icon')
    link(href: 'http://yui.yahooapis.com/pure/0.6.0/pure-min.css', rel: 'stylesheet')
    link(href: '/scripts/basicStopwatch.css', rel: 'stylesheet')
  }
  body {
    div(id: 'layout') {
      div(id: 'main') {
        div(class: 'header') {
          h1('Stopwatch')
        }
        div(class: 'content') {
          div(class: 'controls') {
            button(id: 'startButton', class: 'pure-button button-xlarge', 'Start')
            button(id: 'stopButton', class: 'pure-button button-xlarge', 'Stop')
          }
          h2(id: 'timer0', class: 'timer', '00:00:00.000')
        }
        div(class: 'footer', '')
      }
    }

    script(src: '/scripts/basicStopwatch.js', type: 'text/javascript') { yield '' }
  }
}