# Aws Connection Setup

1. Create Aws Account
2. Install [AWS CLI](https://www.onlinetutorialspoint.com/aws/how-to-install-aws-cli-on-windows-10.html)
3. Configure Aws Credentials
    - write aws configure in cmd
    - add Access_key_id
    - add Secret_access-key
    - add region zone ( ap_south_2 for asia mumbai )
    - add output format (json)

## How to get aws access & secret key

1. [Aws Console](aws.amazon.com)
2. Sign in to console
3. Go to the top right where user name is visible
    - Security credentials
    - Create access key
    - Download the csv file which contains Access key id along with Secret Access Key
    - Paste that keys in cmd->aws configure
