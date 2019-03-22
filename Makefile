
# Image names for docker
CHECK_IMAGE_NAME=fr/android-check
ENV_IMAGE_NAME=fr/android-env
REPORT_IMAGE_NAME=at/reporting

####
check: docker-env-image docker-check-image
report: docker-report-image

docker-env-image:
	@echo ":::Building Android Environment Images"
	docker build --rm -f Env.Dockerfile -t $(ENV_IMAGE_NAME) .

docker-check-image:
	@echo ":::Building Code check Images"
	docker build --rm -f Check.Dockerfile -t $(CHECK_IMAGE_NAME) .

docker-report-image:
	@echo ":::Building Report Images"
	docker build --rm -f Reporting.Dockerfile -t $(REPORT_IMAGE_NAME) .
		