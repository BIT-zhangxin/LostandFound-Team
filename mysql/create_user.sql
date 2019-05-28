CREATE USER 'remoteuser' @'%' IDENTIFIED BY 'remoteuser';
GRANT ALL PRIVILEGES ON LostandFound.* TO 'remoteuser' @'%';