
usage : 
	@echo "USAGE: make build|start|stop"

build :
	cd .. && mvn package
	cp -a ../target/thetvdb-tripleizer-*.war ./thetvdb-tripleizer.war
	docker build -t thetvdb-tripleizer .

start :
	docker run \
		--detach \
		--expose 8080 \
		--name thetvdb \
		thetvdb-tripleizer
	hipache-register thetvdb 8080 || true

stop :
	docker stop thetvdb || true
	docker rm thetvdb || true
	hipache-unregister thetvdb || true
