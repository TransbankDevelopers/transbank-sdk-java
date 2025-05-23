package cl.transbank.webpay.oneclick;

import java.io.IOException;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.QueryBinException;
import cl.transbank.webpay.oneclick.requests.QueryBinRequest;
import cl.transbank.webpay.oneclick.responses.OneclickMallQueryBinResponse;

abstract class OneclickMallBinInfo extends BaseTransaction {
    /**
     * This abstract class represents the OneclickMallBinInfo and provides methods
     * to handle Oneclick Mall Bin Info.
     */
    protected OneclickMallBinInfo(Options options) {
        super(options);
    }

    /**
     * Query for BIN information.
     * 
     * @param tbkUser The inscription id of the user.
     *
     * @return The response from the query bin request.
     * @throws IOException       If there is an error during the execution of the
     *                           request.
     * @throws QueryBinException If there is an error during the start of the
     *                           inscription.
     */
    public OneclickMallQueryBinResponse queryBin(
            String tbkUser) throws IOException, QueryBinException {
        ValidationUtil.hasTextTrimWithMaxLength(
                tbkUser,
                ApiConstants.TBK_USER_LENGTH,
                "tbkUser");

        final WebpayApiRequest request = new QueryBinRequest(tbkUser);
        String endpoint = String.format(
                "%s/bin_info",
                ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(
                    endpoint,
                    HttpUtil.RequestMethod.POST,
                    request,
                    options,
                    OneclickMallQueryBinResponse.class);
        } catch (TransbankException e) {
            throw new QueryBinException(e);
        }
    }
}
