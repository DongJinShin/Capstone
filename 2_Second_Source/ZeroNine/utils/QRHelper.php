<?php
class QRHelper
{
    function makeDataUri($datas)
    {
        $uri = 'http://chart.apis.google.com/chart?';
        $query = "";
        foreach($datas as $key => $val)
        {
            if( strcmp($query, "") != 0 )
            {
                $query .= "&";
            }
            $query .= "$key=$val";
        }
        $uri .= $query;
        return $uri;
    }

    function makeQRUri($EMail)
    {
        if (is_null($EMail))
        {
            return null;
        }
        //$url = rawurlencode($EMail);
        $datas = array("cht" => "qr", "chs" => "300x300", "choe" => "Shift_JIS", "chl" => $EMail);
        $qrUri = $this -> makeDataUri($datas);
        return $qrUri;
    }
}
?>