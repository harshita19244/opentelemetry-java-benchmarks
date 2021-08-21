# Performance test runners
In order to make the test reproducible, they were executed in a set of Equinix bare metal machines sponsored by the CNCF, as follows:
 - x1.small.x86 (1x Intel(R) Xeon(R) CPU E3-1578L v5 @ 2.00GHz, 32GB RAM) Basic with 2GB RAM for Prometheus. 
 - CentOS 8 has been used as the Linux distribution for all machines.
This is a set of scripts used to setup the environment and execute the performance tests. 

## Setting up a remote host

1. Adjust the `REMOTE_HOST` var with the host/IP of the host to execute the tests
1. The remote host has to be configured to accept login with `root` with passwordless SSH keys
1. Run `setup-on-remote.sh`, which installs the tools required for the tests by running the script `setup.sh` on the remote host

## Running the tests

1. Adjust the `REMOTE_HOST` var with the host/IP of the host to execute the tests
1. Execute the `run-on-remote.sh` script, which executes the `runner.sh` on the remote host
