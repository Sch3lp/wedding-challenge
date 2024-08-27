# Part 4

!! Make sure Docker is running !!

## Recap
We have a webapp that serves content to a browser,
using cookies for userstate,
the rest is HATEOAS.

## LiveCoding
### Intro
Start app in debug (for hot-reloading) and show http://localhost:8080, select assignee, complete challenge, select different assignee, complete challenge.

Explain plan:

Create docker container with spring gradle plugin.

Tag to ECR.

Push to ECR.

Explain terraform.

Conclusion.

 
### Docker container with spring gradle plugin
Task "bootBuildImage" from spring gradle plugin

### Explain terraform
ec2.tf :
* uses module.vpc
* exposes port 22 for ssh, and port 80 (not 8080)

ec2-startup.sh :
* uses aws ecr get-login-password to pass along to `docker login`
* for that it needs ecr and ssm iam policy
* docker run -p80:8080 -> why we needed to expose 80 and not 8080
* `--pull=always` because I'm not tagging with versions
* `--restart=on-failure` because I don't want to ssh to manually restart because of some silly reason 

### Executing terraform
The way I did it involves `aws-vault exec kunlabora-sandbox --`.

Doesn't allow terminal input, so we have to export tfplan to a file: -out=tfplan

Applying it then takes in that plan.

Logging in to docker is absolutely wild:
```
aws-vault exec kunlabora-sandbox -- aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 812958718504.dkr.ecr.eu-central-1.amazonaws.com
```

That will allow me to push my image to ECR from my local machine.

## Conclusion

Writing an app in 100% Kotlin is amazing:
1. Use refactor tools when necessary, focus on keeping it simple
2. Imperative Shell Functional Core is great, focus on fully tested core, wrap a kotlinx.html + htmx shell around it.
3. Kotlinx.html + approval tests = epic win
4. Htmx is quite powerful, feel like I have only scratched the surface
5. SpringBoot's router dsl is quite nice, but pattern matching url's is either too magical or too verbose
6. Kotlin Extension functions on ServerRequest or ServerResponse are great for continuing its fluent api
7. Terraform is still quite foggy for me, aws error messages are unhelpful
8. Very lucky to have people that know what they're doing

Thanks for watching!