import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginService} from "./service/api.login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  standalone: true
})
export class LoginComponent {
  loginForm: FormGroup;
  passwordFieldType: string = 'password';

  constructor(private fb: FormBuilder,
              private service: LoginService,
              private router: Router) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  async onSubmit() {
  if (this.loginForm.valid) {
    try {
      await this.service.login(this.loginForm.value);
      await this.router.navigate(['/contas-a-pagar']);
    } catch (err: any) {
      if (err.response && (err.response.status === 401 || err.response.status === 403)) {
        alert('Credenciais inválidas ou você não tem acesso.');
      } else {
        alert('A API de login está indisponível no momento, tente novamente mais tarde.');
      }
      console.error('O LoginService#login retornou o seguinte erro:', err);
    }
  }
}

  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }

}
