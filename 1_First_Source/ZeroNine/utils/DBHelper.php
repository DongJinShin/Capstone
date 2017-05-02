<?php
class DBHelper
{
    private $DBHost = "127.0.0.1";
    private $DBUser = "root";
    private $DBPassword = "Password"; //The security issue can not write
    private $DBName = "ninezero";

    function insertUser($EMail, $Password, $nickname, $QRUri)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        if(!$mysqli -> query("INSERT INTO nz_user (id_nz_user, nz_user_email, nz_user_password, nz_user_nickname, nz_user_qr_uri, nz_user_coin) VALUES (null, '$EMail', '$Password', '$nickname', '$QRUri', 0)"))
        {
            return -1;
        }
        else{
            return $mysqli->insert_id;
        }

        $mysqli -> close();
    }

    function selectOneManagerByEMailPassword($EMail, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_manager as managerID, nz_manager_email as EMail, nz_manager_password as password FROM nz_manager WHERE nz_manager_email = '$EMail' and nz_manager_password = '$password'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else
        {
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserByID($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_nickname as nickname, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE id_nz_user = $ID");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserByEMail($EMail)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_nickname as nickname, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserCoinByEMail($EMail)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo $row['coin'];
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserByEMailPassword($EMail, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_nickname as nickname, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail' and nz_user_password = '$password'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else
        {
            echo "failed";
        }

        $mysqli -> close();
    }

    function saveCoin($EMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_email = '$EMail'"))
        {
            echo "500";
        }
        else
        {
            echo "200";
        }

        $mysqli -> close();
    }

    function useCoin($EMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_email = '$EMail'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail'");
            if($result -> num_rows > 0)
            {
                $row = mysqli_fetch_assoc($result);
                if($row['coin'] < 0)
                {
                    mysqli_rollback($mysqli);
                    echo "502";
                }
                else
                {
                    mysqli_commit($mysqli);
                    echo "200";
                }
            }
            else
            {
                mysqli_rollback($mysqli);
                echo "501";
            }
        }

        $mysqli -> close();
    }

    function giftCoin($FromEMail, $DSTEMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_email = '$FromEMail'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$FromEMail'");
            if($result -> num_rows > 0)
            {
                $row = mysqli_fetch_assoc($result);
                if($row['coin'] < 0)
                {
                    mysqli_rollback($mysqli);
                    echo "502";
                }
                else
                {
                    $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$DSTEMail'");
                    if($result -> num_rows > 0)
                    {
                        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_email = '$DSTEMail'"))
                        {
                            mysqli_rollback($mysqli);
                            echo "504";
                        }
                        else
                        {
                            mysqli_commit($mysqli);
                            echo "200";
                        }
                    }
                    else
                    {
                        mysqli_rollback($mysqli);
                        echo "503";
                    }
                }
            }
            else
            {
                mysqli_rollback($mysqli);
                echo "501";
            }
        }

        $mysqli -> close();
    }
}
?>