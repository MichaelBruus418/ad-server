# Ad Server

A proof-of-concept ad-server, written in Scala using the Play framwork and an MySQL db.
This was part of my thesis in Computer Science (2023), at Business Academy Aarhus.

The ad-server handles business logic for which ads to display, while also functioning as a webserver.
Ad clients/consumers can display the ads in an iframe, or - experimentally - recieve the html as JSON.

Template for DB setup and data files:
https://github.com/MichaelBruus418/ad-server/tree/a81a73ed6f59192d56bfe9bfd437c6813bd3cd16/conf/sql

To request and display ads, I made a very basic website:
https://github.com/MichaelBruus418/ad-consumer


