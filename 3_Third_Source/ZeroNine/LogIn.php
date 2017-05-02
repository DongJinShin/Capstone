<?php
include 'utils/DBHelper.php';

$EMail = $_POST['EMail'];
$password = $_POST['password'];
$userType = $_POST['userType'];

$DBHelper = new DBHelper();

if($userType == 0)
{
    $DBHelper ->selectOneManagerByEMailPassword($EMail, $password);
}
else
{
    $DBHelper ->selectOneUserByEMailPassword($EMail, $password);
}
?>