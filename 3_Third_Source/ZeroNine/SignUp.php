<?php
include 'utils/QRHelper.php';
include 'utils/DBHelper.php';

$EMail = $_POST['EMail'];
$password = $_POST['password'];
$nickname = $_POST['nickname'];

$QRHelper = new QRHelper();
$DBHelper = new DBHelper();

$QRUri = $QRHelper->makeQRUri($EMail);

$lastInsertID = $DBHelper -> insertUser($EMail, $password, $nickname, $QRUri);

if($lastInsertID != -1)
{
    $DBHelper ->selectOneUserByID($lastInsertID);
}
else
{
    echo "failed";
}

?>


