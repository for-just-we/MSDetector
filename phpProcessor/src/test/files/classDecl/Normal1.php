<?php
class AuthenticationConfigTest extends PmaTestCase
{
    private $object;


    public function testAuthFails()
    {
        $removeConstant = false;
        $GLOBALS['error_handler'] = new ErrorHandler();
        $GLOBALS['cfg']['Servers'] = [1];
        $GLOBALS['allowDeny_forbidden'] = false;

        $dbi = $this->getMockBuilder('PhpMyAdmin\DatabaseInterface')
            ->disableOriginalConstructor()
            ->getMock();
        $GLOBALS['dbi'] = $dbi;

        ob_start();
        $this->object->showFailure('');
        $html = ob_get_clean();


    }
}
?>