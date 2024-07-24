sudo apt update
sudo apt install -y awscli
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
apt-cache policy docker-ce
sudo apt install docker-ce
sudo usermod -aG docker ubuntu
aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 812958718504.dkr.ecr.eu-central-1.amazonaws.com
docker pull 812958718504.dkr.ecr.eu-central-1.amazonaws.com/kunlaquota-repo:springboot-ec2
docker run -d -p80:8080 --pull=always --restart=on-failure 812958718504.dkr.ecr.eu-central-1.amazonaws.com/kunlaquota-repo:springboot-ec2
