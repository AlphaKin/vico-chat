import store from '../../store'
import axios from 'axios'
import Qs from 'qs'


class Request{
    constructor(baseURL){
        this.baseURL = baseURL;
        this._type = 'post';
        this._url = 'http://localhost:/8080';
        this._headers = {}
    }

    post(path, params, headers){
        this._url = this.baseURL + path;
        this._type = 'post';
        params && (this._params = Qs.stringify(params));
        headers && (this._headers = this.headers);
        return this;
    }

    header(key, value){
        key && value && (this._headers[key] = value);
        return this;
    }

    token(token = null){
        if(token){
            this._headers['Authorization'] = 'Bearer ' + token
        }else{
            let storeToken = store.state.userInfo.token;
            storeToken && (this._headers['Authorization'] = 'Bearer ' + storeToken)
            console.log(this._headers['Authorization']);
        }
        return this;
    }

    apply(){
        return new Promise((resolve, reject) => {
            axios[this._type](this._url, this._params, { headers: this._headers})
            .then((resp) => {
                let data = resp.data;
                if(data.code === 1){
                    resolve(data)
                }else{
                    reject(data)
                }
            })
            .catch((error) => {
                reject({msg: error});
            });
        });
    }
    
}

export default { Request }