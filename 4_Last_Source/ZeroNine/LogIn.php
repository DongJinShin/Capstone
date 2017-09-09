<?php
include 'utils/DBHelper.php';

$ID = $_POST['ID'];
$password = $_POST['password'];
$userType = $_POST['userType'];

$DBHelper = new DBHelper();

if($userType == 0)
{
    $DBHelper ->selectOneManagerByIDPassword($ID, $password);
}
else
{
    $DBHelper ->selectOneUserByIDPassword($ID, $password);
}
?>