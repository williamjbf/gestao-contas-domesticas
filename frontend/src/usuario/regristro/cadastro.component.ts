import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CadastroService} from "./service/api.cadastro.service";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css'],
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  standalone: true
})
export class CadastroComponent {
  cadastroForm: FormGroup;
  passwordFieldType: string = 'password';

  constructor(private fb: FormBuilder,
              private service: CadastroService,
              private router: Router) {
    this.cadastroForm = this.fb.group({
      login: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  async onSubmit() {
    if (this.cadastroForm.valid) {
      try {
        await this.service.cadastro(this.cadastroForm.value);
        await this.router.navigate(['/']);
      } catch (err: any) {
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
          alert('Não foi possivel realizar o cadastro.');
        } else {
          alert('A API de cadastro está indisponível no momento, tente novamente mais tarde.');
        }
        console.error('O UsuarioService#Cadastro retornou o seguinte erro:', err);
      }
    }
  }

  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }

}
