yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    meta(charset:'utf-8')
    title("Stopwatch")

    meta(name: 'apple-mobile-web-app-title', content: 'Stopwatch')
    meta(name: 'description', content: '')
    meta(name: 'viewport', content: 'width=device-width, initial-scale=1')

    script(src: 'http://code.jquery.com/jquery-latest.min.js', type: 'text/javascript') { yield '' }

    link(href: '/images/favicon.ico', rel: 'shortcut icon')
  }
  body {

    div(class: 'stopwatch') { yield '' }

    script(src: '/scripts/stopwatch.js', type: 'text/javascript') { yield '' }

  }
}
