<?php
include 'utils/DBHelper.php';

$ID = $_POST['ID'];
$deviceID = $_POST['deviceID'];

$DBHelper = new DBHelper();

$lastInsertID = $DBHelper -> insertFCMID($ID, $deviceID);

if($lastInsertID != -1)
{
    echo "success";
}
else
{
    echo "failed";
}

?>


