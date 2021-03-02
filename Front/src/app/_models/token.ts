export class Token {
    //non conventional fields names to match jwt ones from auth server
    access_token: string;
    token_type: string;
    refresh_token: string;
    expires_in: number;
    scope: string;
    userId: number;
    jti: string;

    constructor(jwt: Token) {
        this.access_token = jwt.access_token;
        this.token_type = jwt.token_type;
        this.refresh_token = jwt.refresh_token;
        this.expires_in = jwt.expires_in;
        this.scope = jwt.scope;
        this.userId = jwt.userId;
        this.jti = jwt.jti;
    }

}