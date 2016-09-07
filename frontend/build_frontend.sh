Docker build -t registry.swarm.devfactory.com/devfactory/workpattern_frontend:1 -f Dockerfile .
Docker push registry.swarm.devfactory.com/devfactory/workpattern_frontend:1
export DOCKER_HOST="tcp://webserver.devfactory.com"
docker service rm workpattern_frontend_service
docker service create --name workpattern_frontend_service -p 16596:8080 registry.swarm.devfactory.com/devfactory/workpattern_frontend:1
docker service scale workpattern_frontend_service=2
unset DOCKER_HOST
