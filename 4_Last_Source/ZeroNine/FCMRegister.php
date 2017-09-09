<?php
include 'utils/DBHelper.php';

$registrationID = $_POST['registrationID'];
$deviceID = $_POST['deviceID'];

$DBHelper = new DBHelper();

$lastInsertID = $DBHelper -> insertFCM($registrationID, $deviceID);

if($lastInsertID != -1)
{
    echo "success";
}
else
{
    echo "failed";
}

?>


