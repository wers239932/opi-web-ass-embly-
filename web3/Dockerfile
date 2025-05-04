FROM jboss/wildfly:20.0.1.Final

COPY target/ROOT.war /opt/jboss/wildfly/standalone/deployments/


COPY init.sql /docker-entrypoint-initdb.d/


EXPOSE 8080
EXPOSE 5432
USER root
RUN curl -o /opt/jboss/wildfly/standalone/deployments/postgresql-42.5.1.jar https://jdbc.postgresql.org/download/postgresql-42.5.1.jar
USER jboss
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]

